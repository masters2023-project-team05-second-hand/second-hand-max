import LoadingIndicator from "@assets/image/loading.gif";
import TopBar from "@components/TopBar";
import { ROUTE_PATH } from "@router/constants";
import { Page, SubTitle } from "@styles/common";
import { useQuery } from "@tanstack/react-query";
import { postSocialLogin } from "api";
import { useSetAtom } from "jotai";
import { useNavigate, useParams } from "react-router-dom";
import { addressListAtom, memberAtom } from "store";
import styled from "styled-components";

export function Auth() {
  const { provider } = useParams<{ provider: "kakao" | "github" }>();
  const accessCode = new URL(window.location.href).searchParams.get("code");
  const navigate = useNavigate();

  const setMember = useSetAtom(memberAtom);
  const setAddresses = useSetAtom(addressListAtom);

  const getUser = async () => {
    if (!accessCode || !provider) {
      return;
    }

    const {
      data: { tokens, addresses, member },
    } = await postSocialLogin(provider, { accessCode });
    const isFirstLogin = addresses?.length === 0;

    localStorage.setItem("accessToken", tokens.accessToken);
    localStorage.setItem("refreshToken", tokens.refreshToken);
    localStorage.setItem(
      "user",
      JSON.stringify({
        addresses,
        member,
      })
    );

    setMember(member);
    setAddresses(addresses);

    return isFirstLogin;
  };

  const queryKey = ["socialLogin", provider, accessCode];

  const { isLoading } = useQuery(queryKey, getUser, {
    enabled: !!accessCode && !!provider,
    onSuccess: (isFirstLogin) => {
      isFirstLogin ? navigate(ROUTE_PATH.register) : navigate(ROUTE_PATH.home);
    },
  });

  if (isLoading) {
    return (
      <Page>
        <TopBar
          title="내 계정"
          backgroundColor="neutralBackgroundBlur"
          isWithBorder={true}
        />
        <Loading>
          <img src={LoadingIndicator} alt="loading-indicator" />
          <SubTitle>로그인중입니다...</SubTitle>
          <SubTitle>새로고침을 하지 마세요!</SubTitle>
        </Loading>
      </Page>
    );
  }

  return null;
}

const Loading = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 100px;

  img {
    margin: 30px;
  }
`;
