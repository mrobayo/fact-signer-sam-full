import React from "react";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import ListItemButton from "@mui/material/ListItemButton";

const SideMenuButton: React.FC<{
    primary?: React.ReactNode;
    icon?: React.ReactNode;
    onClick?:React.MouseEventHandler;
}> = ({ primary, icon, onClick }) => {
    return (
        <ListItemButton onClick={onClick}>
            <ListItemIcon>
                {icon}
            </ListItemIcon>
            <ListItemText primary={primary} />
        </ListItemButton>
    );
};

export default SideMenuButton;
