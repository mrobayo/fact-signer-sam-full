import * as React from 'react';
import ListSubheader from '@mui/material/ListSubheader';
import PointOfSaleTwoToneIcon from '@mui/icons-material/PointOfSaleTwoTone';
import SideMenuButton from "./SideMenuButton/SideMenuButton.tsx";
import ColorLensTwoToneIcon from '@mui/icons-material/ColorLensTwoTone';
import RuleFolderTwoToneIcon from '@mui/icons-material/RuleFolderTwoTone';
import PeopleAltTwoToneIcon from '@mui/icons-material/PeopleAltTwoTone';

export const secondaryListItems = (
  <React.Fragment>
      <ListSubheader component="div" inset> Sistema  </ListSubheader>
      <SideMenuButton primary="Arqueo Caja" icon={<PointOfSaleTwoToneIcon />} />
      <SideMenuButton primary="Clientes" icon={<PeopleAltTwoToneIcon />} />
      <SideMenuButton primary="Productos" icon={<ColorLensTwoToneIcon />} />
      <SideMenuButton primary="Administracion" icon={<RuleFolderTwoToneIcon />} />
  </React.Fragment>
);
