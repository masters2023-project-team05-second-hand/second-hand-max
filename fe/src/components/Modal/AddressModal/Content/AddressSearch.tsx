import { ListItem, ListPanel } from "@components/Modal/Modal.style";
import { AddressInfo } from "api/type";
import { useSetAtom } from "jotai";
import { addresses1 } from "mocks/data/address";
import { useState } from "react";
import { addressListAtom, currentAddressIdAtom } from "store";
import styled from "styled-components";

export default function AddressSearch({
  userAddressIDs,
  closeAddressSearch,
}: {
  userAddressIDs: number[];
  closeAddressSearch: () => void;
}) {
  const setAddresses = useSetAtom(addressListAtom);
  const setCurrentAddressId = useSetAtom(currentAddressIdAtom);

  const onAddAddress = (item: AddressInfo) => {
    setAddresses((prev) => {
      const isMaxAddressCount = prev.length === 2;
      const isAlreadyAdded = prev.some(({ id }) => id === item.id);
      if (isMaxAddressCount || isAlreadyAdded) {
        return prev;
      }

      setCurrentAddressId(item.id);
      return [...prev, item];
    });
  };
  // TODO: 동네 검색/전체 동네 조회 요청
  const [allAddressList] = useState(addresses1.addresses);
  // const [searchKeyword, setSearchKeyword] = useState<string>("");

  return (
    <>
      <SearchBar placeholder="동명(읍, 면)으로 검색(ex. 서초동)" />
      <ListPanel>
        {allAddressList.map(({ id, name }) => (
          <ListItem
            key={id}
            $active={userAddressIDs.includes(id)}
            onClick={() => {
              onAddAddress({ id, name });
              closeAddressSearch();
            }}>
            <span>{name}</span>
          </ListItem>
        ))}
      </ListPanel>
    </>
  );
}

const SearchBar = styled.input`
  width: 100%;
  height: 40px;
  padding: 8px;
  border-radius: ${({ theme: { radius } }) => radius[8]};
  background-color: ${({ theme: { color } }) => color.neutralBackgroundBold};

  &::placeholder {
    font: ${({ theme: { font } }) => font.availableDefault16};
    color: ${({ theme: { color } }) => color.neutralTextWeak};
  }
`;
