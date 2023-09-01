export const isSameItems = (a: number[], b: number[]) => {
  const setA = new Set(a);
  const setB = new Set(b);

  return setA.size === setB.size && [...setA].every((value) => setB.has(value));
};
