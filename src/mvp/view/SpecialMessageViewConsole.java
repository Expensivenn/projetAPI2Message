package mvp.view;

import classemetiers.Message;

public interface SpecialMessageViewConsole {
    Message saisieMessage();
    //SGBD
    void SgbdEnvoyerMessage();
    void SgbdEnvoyerReponse();
    void SgbdAsReponse();
    void SgbdNombreReponse();
    void SgbdReponseMessage();
}
