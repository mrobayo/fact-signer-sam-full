import React from 'react';
import {alpha, styled} from '@mui/material/styles';
import PropTypes from 'prop-types';
import Toolbar, { ToolbarProps } from '@mui/material/Toolbar';

export const TopToolbar: React.FC<ToolbarProps> = (props) => <StyledToolbar {...props} />;

TopToolbar.propTypes = {
    children: PropTypes.node,
    className: PropTypes.string,
};

export default TopToolbar;
const PREFIX = 'RaTopToolbar';

const StyledToolbar = styled(Toolbar, {
    name: PREFIX,
    overridesResolver: (_props, styles) => styles.root,
})(({ theme }) => ({
    display: 'flex',
    justifyContent: 'flex-end',
    alignItems: 'flex-end',
    gap: theme.spacing(1),
    whiteSpace: 'nowrap',
    flex: '0 1 auto',
    minHeight: theme.spacing(8),
    bgcolor: alpha(theme.palette.primary.main, theme.palette.action.activatedOpacity),
    [theme.breakpoints.up('md')]: {
        padding: theme.spacing(0.5),
        paddingTop: theme.spacing(1),
    },
    [theme.breakpoints.down('md')]: {
        flex: '0 1 100%',
        padding: theme.spacing(0.5),
        paddingBottom: theme.spacing(1),
    },
    [theme.breakpoints.down('sm')]: {
        backgroundColor: theme.palette.background.paper,
    },
}));
