import React from 'react';
import { History } from 'history';
import { NotificationContextProvider } from '../notification';
import {AdminRouter} from "../routing";

export interface CoreAdminContextProps {
    basename?: string;
    history?: History;
    children?: React.ReactNode;
}

export const CoreAdminContext = (props: CoreAdminContextProps) => {
    const {
        basename,
        history,
        children
    } = props;

    return (
        <AdminRouter history={history} basename={basename}>
            <NotificationContextProvider>{children}</NotificationContextProvider>
        </AdminRouter>
    //     <AuthContext.Provider value={finalAuthProvider}>
    //         <DataProviderContext.Provider value={finalDataProvider}>
    //             <StoreContextProvider value={store}>
    //                 <PreferencesEditorContextProvider>
    //                     <QueryClientProvider client={finalQueryClient}>
    //                         <AdminRouter history={history} basename={basename}>
    //                             <I18nContextProvider value={i18nProvider}>
    //                                 <NotificationContextProvider>
    //                                     {/*<ResourceDefinitionContextProvider>*/}
    //                                         {children}
    //                                     {/*</ResourceDefinitionContextProvider>*/}
    //                                 </NotificationContextProvider>
    //                             </I18nContextProvider>
    //                         </AdminRouter>
    //                     </QueryClientProvider>
    //                 </PreferencesEditorContextProvider>
    //             </StoreContextProvider>
    //         </DataProviderContext.Provider>
    //     </AuthContext.Provider>
    );
};
