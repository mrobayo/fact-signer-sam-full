// ?page=1&size=5&sort=published,desc&sort=title,asc
export function buildQueryByPage(page: number, size: number, sort: string[]) {
  const sorts = (sort.length > 0 ? '&': '') + sort.map(field => `sort=${field}`).join('&');
  return `?page=${page}&size=${size}${sorts}`;
}

