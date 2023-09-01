import { Dim } from "@styles/common";
import React from "react";
import { createPortal } from "react-dom";
import styled from "styled-components";
import ModalHeader, { ModalHeaderProps } from "./ModalHeader";

type ModalProps = {
  headerProps: ModalHeaderProps;
  content: React.ReactNode;
  closeHandler: () => void;
};

export default function Modal({ headerProps, content }: ModalProps) {
  return createPortal(
    <Dim>
      <StyledModal>
        <ModalHeader {...headerProps} />
        <ModalContent>{content}</ModalContent>
      </StyledModal>
    </Dim>,
    document.getElementById("modal-root")!
  );
}

const StyledModal = styled.div`
  position: fixed;
  top: calc(50% - 350px);
  left: calc(50% - 160px);
  width: 320px;
  height: 700px;
  background-color: ${({ theme: { color } }) => color.neutralBackground};
  border-radius: ${({ theme: { radius } }) => radius[16]};
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
`;

const ModalContent = styled.main`
  min-height: 264px;
  max-height: 600px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 0px 24px;
`;
