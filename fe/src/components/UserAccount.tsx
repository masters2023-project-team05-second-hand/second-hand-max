import { useMember } from "store";
import ProfileUploadButton from "./ProfileUploadButton";

export default function UserAccount() {
  const member = useMember();

  return (
    <>
      <ProfileUploadButton />
      <span>{member?.nickname}</span>
    </>
  );
}
