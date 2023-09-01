import { API_PATH } from "./constants";
import { fetcher } from "./fetcher";
import { AuthInfo, UserAddressInfo } from "./type";

export const postSocialLogin = async (
  provider: "kakao" | "github",
  body: { accessCode: string }
) => {
  return await fetcher.post<AuthInfo>(API_PATH.login(provider), body);
};

export const postLogout = async (body: { refreshToken: string }) => {
  return await fetcher.post(API_PATH.logout, body);
};

export const postRefreshToken = async (refreshToken: string) => {
  return await fetcher.post<{ accessToken: string }>(API_PATH.refresh, {
    refreshToken,
  });
};

export const postUserAddress = async (body: { addressIds: number[] }) => {
  return await fetcher.post<UserAddressInfo[]>(API_PATH.userAddress, body);
};

export const postUserProfile = async (file: File) => {
  const config = {
    headers: {
      "content-type": "multipart/form-data",
    },
  };
  const formData = new FormData();
  formData.append("image", file);

  return await fetcher.post<{ profileImgUrl: string }>(
    API_PATH.userProfile,
    formData,
    config
  );
};
