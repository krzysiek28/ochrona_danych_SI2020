package com.od.cryptography.exercises_module_2.controller;

import com.od.cryptography.common.errors.ValidationError;
import com.od.cryptography.exercises_module_2.model.Action;
import com.od.cryptography.exercises_module_2.model.CryptographyModel;
import com.od.cryptography.exercises_module_2.model.EncryptionType;
import com.od.cryptography.exercises_module_2.service.CryptographyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/exercises2")
public class CryptographyController {

    private final CryptographyService service;

    public CryptographyController(CryptographyService service) {
        this.service = service;
    }

    @GetMapping
    public String cryptographyView(Model model) {
        model.addAttribute("appModel", new CryptographyModel());
        return "exercises2";
    }

    @PostMapping
    public String operation(@ModelAttribute CryptographyModel appModel, RedirectAttributes redirectAttributes) throws ValidationError {
        String result;

        if(EncryptionType.CESAR.equals(appModel.getEncryptionType())){
            result = resolveCezar(appModel);
        } else if(EncryptionType.VINEGERE.equals(appModel.getEncryptionType())){
            result = resolveVinegere(appModel);
        } else {
            throw new ValidationError("Nie wybrano szyfrowania");
        }

        redirectAttributes.addFlashAttribute("resultText", result);
        redirectAttributes.addFlashAttribute("inputModel", appModel);
        return "redirect:/exercises2";
    }

    private String resolveCezar(@ModelAttribute CryptographyModel appModel) throws ValidationError {
        String result;
        if(Action.ENCRYPTION.equals(appModel.getAction())){
            result = service.cesarEncryption(appModel.getText(), appModel.getShift());
        } else if(Action.DECRYPTION.equals(appModel.getAction())){
            result = service.cesarDecryption(appModel.getText(), appModel.getShift());
        } else {
            throw new ValidationError("Nie wybrano akcji");
        }
        return result;
    }

    private String resolveVinegere(@ModelAttribute CryptographyModel appModel) throws ValidationError {
        String result;
        if(Action.ENCRYPTION.equals(appModel.getAction())){
            result = service.vinegereEncryption(appModel.getText(), appModel.getKey());
        } else if(Action.DECRYPTION.equals(appModel.getAction())){
            result = service.vinegereDecryption(appModel.getText(), appModel.getKey());
        } else {
            throw new ValidationError("Nieznana akcja");
        }
        return result;
    }
}
