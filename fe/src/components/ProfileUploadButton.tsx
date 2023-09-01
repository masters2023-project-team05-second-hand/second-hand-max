import { ReactComponent as CameraIcon } from "@assets/icon/camera.svg";
import { useMutation } from "@tanstack/react-query";
import { postUserProfile } from "api";
import { useSetAtom } from "jotai";
import { memberAtom, useMember } from "store";
import styled from "styled-components";

export default function ProfileUploadButton() {
  const member = useMember();
  const setMember = useSetAtom(memberAtom);

  const userProfileMutation = useMutation(
    (file: File) => postUserProfile(file),
    {
      onSuccess: (res) => {
        const newProfileImageUrl = res.data.profileImgUrl;
        setMember((prev) => ({
          ...prev,
          profileImgUrl: newProfileImageUrl,
        }));
      },
      onError: (error) => {
        console.error("Error uploading profile image:", error);
      },
    }
  );

  const onChangeProfileImage = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];

    if (file) {
      userProfileMutation.mutate(file);
    }
  };

  return (
    <StyledProfile>
      <ProfileUpload htmlFor="profile-upload-input">
        {member?.profileImgUrl && <ProfileImage src={member?.profileImgUrl} />}
        <CameraIcon className="camera-icon" />
        <input
          type="file"
          id="profile-upload-input"
          onChange={onChangeProfileImage}
        />
      </ProfileUpload>
    </StyledProfile>
  );
}

const StyledProfile = styled.div`
  display: flex;
  position: relative;
  width: 100px;
  height: 100px;
`;

const ProfileUpload = styled.label`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  border-radius: ${({ theme: { radius } }) => radius.half};
  background-color: ${({ theme: { color } }) => color.neutralOverlay};
  border: 1px solid ${({ theme: { color } }) => color.neutralBorder};

  .camera-icon {
    filter: ${({ theme: { filter } }) => filter.accentText};
  }

  #profile-upload-input {
    display: none;
  }
`;

const ProfileImage = styled.img`
  width: 100%;
  height: 100%;
  position: absolute;
  object-fit: cover;
  cursor: pointer;
  border-radius: 50%;
  border: 1px solid ${({ theme }) => theme.color.neutralBorder};
  filter: brightness(0.8);
`;
