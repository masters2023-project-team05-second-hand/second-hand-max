import React from "react";
import { styled } from "styled-components";
import { PLACE_HOLDER } from "./constants";
import { AddressInfo } from "@api/type";
import useAutoHeight from "@hooks/useAutoHeight";
import { getLastWord } from "@utils/index";

export default function ProductRegisterContent({
  content,
  address,
  onChange,
}: {
  content?: string;
  address?: AddressInfo;
  onChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
}) {
  const { ref: textAreaRef, onChange: onContentChange } =
    useAutoHeight<HTMLTextAreaElement>(onChange);

  return (
    <Content
      placeholder={PLACE_HOLDER.content(
        getLastWord(address?.name) ?? PLACE_HOLDER.defaultPlace
      )}
      value={content}
      name="content"
      onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) =>
        onContentChange(e)
      }
      ref={textAreaRef}
    />
  );
}

const Content = styled.textarea`
  color: ${({ theme: { color } }) => color.neutralTextStrong};
  font: ${({ theme: { font } }) => font.availableDefault16};
  min-height: 120px;
  border-radius: ${({ theme: { radius } }) => radius[8]};
  padding: 8px;

  &:focus {
    background-color: ${({ theme: { color } }) => color.neutralBackgroundBold};
  }
`;
