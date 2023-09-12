import dayjs from "dayjs";

export function getAge(date: Date|string|null): number {
  const meses = dayjs().diff(date, "month");
  return Math.floor(meses/12);
}