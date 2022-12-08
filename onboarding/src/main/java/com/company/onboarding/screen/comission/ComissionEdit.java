package com.company.onboarding.screen.comission;

import io.jmix.ui.screen.*;
import com.company.onboarding.entity.Comission;

@UiController("Comission.edit")
@UiDescriptor("comission-edit.xml")
@EditedEntityContainer("comissionDc")
public class ComissionEdit extends StandardEditor<Comission> {
}