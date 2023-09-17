import isArray from "lodash/isArray";

// ?page=1&size=5&sort=published,desc&sort=title,asc
export function buildQueryByPage(page: number, size: number, sort: string[]) {
  const sorts = (sort.length > 0 ? '&': '') + sort.map(field => `sort=${field}`).join('&');
  return `?page=${page}&size=${size}${sorts}`;
}

export function buildQuery(parameters: Record<string, number|string|string[]>) {
  const searchParams = new URLSearchParams();
  Object.entries(parameters).forEach(([key, value]) => {
    if (isArray(value)) {
      value.forEach(valueItem => searchParams.append(key, valueItem));
    } else if (value !== undefined && value !== null && value !== '') {
      searchParams.append(key, `${value}`);
    }
  })
  return searchParams.toString();
}
