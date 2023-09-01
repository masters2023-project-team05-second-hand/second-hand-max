import { useMutation } from "@tanstack/react-query";
import { isSameItems } from "@utils/index";
import { postUserAddress } from "api";
import { useState } from "react";
import { useAddressList } from "store";
import Modal from "../Modal";
import AddressIndicatorList from "./Content/AddressIndicatorList";
import AddressSearch from "./Content/AddressSearch";

export default function AddressModal({
  closeHandler,
}: {
  closeHandler: () => void;
}) {
  const addresses = useAddressList();
  const userAddressIDs = addresses.map(({ id }) => id);

  const [prevAddressIDs] = useState(userAddressIDs);
  const [isSearchingAddress, setIsSearchingAddress] = useState<boolean>(false);

  const openAddressSearch = () => setIsSearchingAddress(true);
  const closeAddressSearch = () => setIsSearchingAddress(false);

  const userAddressMutation = useMutation(
    () => postUserAddress({ addressIds: userAddressIDs }),
    {
      onMutate: () => console.log("onMutate"),
      onSuccess: () => console.log("onSuccess"),
      onError: () => console.log("onError"),
    }
  );

  const addressSearchHeaderProps = {
    backHandler: closeAddressSearch,
    closeHandler,
  };

  const addressIndicatorListHeaderProps = {
    title: "동네 설정",
    closeHandler: () => {
      !isSameItems(userAddressIDs, prevAddressIDs) &&
        userAddressMutation.mutate();
      closeHandler();
    },
  };

  const currentHeaderProps = isSearchingAddress
    ? addressSearchHeaderProps
    : addressIndicatorListHeaderProps;

  const currentContent = isSearchingAddress ? (
    <AddressSearch {...{ closeAddressSearch, userAddressIDs }} />
  ) : (
    <AddressIndicatorList openAddressSearch={openAddressSearch} />
  );

  return (
    <Modal
      headerProps={currentHeaderProps}
      content={currentContent}
      closeHandler={closeHandler}
    />
  );
}
