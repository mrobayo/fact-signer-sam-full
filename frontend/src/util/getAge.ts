import dayjs from "dayjs";

export function getAge(date: Date|string|null): number {
  console.log(dayjs());
  const meses = dayjs().diff(date, "month");

  console.log(date, meses);

  return Math.floor(meses/12);
}