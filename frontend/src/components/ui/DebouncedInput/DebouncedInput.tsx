import React, {useEffect, useRef, useState} from "react";
import InputAdornment from "@mui/material/InputAdornment";
import SearchIcon from "@mui/icons-material/Search";
import OutlinedInput from "@mui/material/OutlinedInput";
import debounce from "lodash/debounce";

interface DebouncedInputProps {
  value: string;
  setValue: (value: string) => void;
}

export default function DebouncedInput({value, setValue}: DebouncedInputProps) {
  const [text, setText] = useState(value);

  const debouncedSearch = useRef(
    debounce(async (text) => setValue(text), 500)).current;
  useEffect(() => () => debouncedSearch.cancel(), [debouncedSearch]);

  return (
    <OutlinedInput
      value={text}
      onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
        setText(event.target.value);
        debouncedSearch(event.target.value);
      }}
      sx={{ flexGrow: 1 }}
      autoFocus
      placeholder="Buscar por Identificacion/Nombre..."
      startAdornment={<InputAdornment position="start"><SearchIcon /></InputAdornment>}
    />
  );
}
