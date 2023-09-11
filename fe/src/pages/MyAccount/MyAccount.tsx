import NavigationBar from "@components/NavigationBar";
import TopBar from "@components/TopBar";
import { Page } from "@styles/common";
import { useMemberValue } from "store";
import Login from "./Login";
import Setting from "./Setting";

export default function MyAccount() {
  const member = useMemberValue();

  return (
    <Page>
      <TopBar
        title="내 계정"
        backgroundColor="neutralBackgroundBlur"
        isWithBorder={true}
      />
      {member.nickname && <Setting />}
      {!member.nickname && <Login />}
      <NavigationBar />
    </Page>
  );
}
