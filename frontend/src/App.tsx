import MainLayout from "./layout/MainLayout/MainLayout.tsx";
import {css} from "@emotion/react";

function App() {
  return (
      <MainLayout>
        // body
        <div css={css`height: 1200px; `}>
           this is body 2
        </div>
      </MainLayout>
  )
}

export default App
