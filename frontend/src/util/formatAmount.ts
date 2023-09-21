
export function formatAmount(value: number|string, decimals: number = 2) {
  return (+value).toFixed(decimals);
}
