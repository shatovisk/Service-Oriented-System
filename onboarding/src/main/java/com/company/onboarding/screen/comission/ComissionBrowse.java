package com.company.onboarding.screen.comission;

import io.jmix.ui.screen.*;
import com.company.onboarding.entity.Comission;

@UiController("Comission.browse")
@UiDescriptor("comission-browse.xml")
@LookupComponent("comissionsTable")
public class ComissionBrowse extends StandardLookup<Comission> {
}