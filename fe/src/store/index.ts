import { AddressInfo, Member, UserAddressInfo } from "api/type";
import { useAtomValue } from "jotai";
import { atomWithDefault } from "jotai/utils";

const { member, addresses } = JSON.parse(localStorage.getItem("user") || "{}");
const currentAddressId =
  addresses &&
  addresses.find((address: UserAddressInfo) => address.isLastVisited)?.id;

export const memberAtom = atomWithDefault<Member>(() => member);
export const addressListAtom = atomWithDefault<AddressInfo[]>(() => addresses);
export const currentAddressIdAtom = atomWithDefault<number | null>(
  () => currentAddressId
);

export const useMember = () => useAtomValue(memberAtom);
export const useAddressList = () => useAtomValue(addressListAtom);
export const useCurrentAddressId = () => useAtomValue(currentAddressIdAtom);
