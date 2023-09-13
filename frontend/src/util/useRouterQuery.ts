import React from "react";
import {useLocation} from "react-router-dom";

export function useRouterQuery() {
  const { search, pathname, ...location} = useLocation();
  const segments = pathname.slice(1).split('/');
  const query = React.useMemo(() => new URLSearchParams(search), [search]);

  return { ...location, search, pathname, segments, query, isNew: segments.at(-1) === 'new' };
}