package com.company.onboarding.screen.myonboarding;

import com.company.onboarding.entity.PersonStep;
import com.company.onboarding.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.CheckBox;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.Label;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@UiController("MyOnboardingScreen")
@UiDescriptor("my-onboarding-screen.xml")
public class MyOnboardingScreen extends Screen {
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private CollectionLoader<PersonStep> personStepsDl;
    @Autowired
    private Label completedStepsLabel;
    @Autowired
    private Label overdueStepsLabel;
    @Autowired
    private Label totalStepsLabel;
    @Autowired
    private CollectionContainer<PersonStep> personStepsDc;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        User user = (User) currentAuthentication.getUser();
        personStepsDl.setParameter("user", user);
        personStepsDl.load();
        updateLabels();
    }

    @Subscribe(id = "personStepsDc", target = Target.DATA_CONTAINER)
    public void onPersonStepsDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<PersonStep> event) {
        updateLabels();
    }

    @Autowired
    private UiComponents uiComponents;

    @Install(to = "personStepsTable.completed", subject = "columnGenerator")
    private Component personStepsTableCompletedColumnGenerator(PersonStep personStep) {
        CheckBox checkBox = uiComponents.create(CheckBox.class);
        checkBox.setValue(personStep.getCompletedDate() != null);
        checkBox.addValueChangeListener(e -> {
            if (personStep.getCompletedDate() == null) {
                personStep.setCompletedDate(LocalDate.now());
            } else {
                personStep.setCompletedDate(null);
            }
        });
        return checkBox;
    }

    private void updateLabels() {
        totalStepsLabel.setValue("Total tasks: " + personStepsDc.getItems().size());

        long completedCount = personStepsDc.getItems().stream()
                .filter(us -> us.getCompletedDate() != null)
                .count();
        completedStepsLabel.setValue("Completed tasks: " + completedCount);

        long overdueCount = personStepsDc.getItems().stream()
                .filter(us -> isOverdue(us))
                .count();
        overdueStepsLabel.setValue("Overdue tasks: " + overdueCount);
    }

    private boolean isOverdue(PersonStep us) {
        return us.getCompletedDate() == null
                && us.getDueDate() != null
                && us.getDueDate().isBefore(LocalDate.now());
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(Button.ClickEvent event) {
        dataContext.commit();
        close(StandardOutcome.COMMIT);
    }

    @Autowired
    private DataContext dataContext;

    @Subscribe("discardButton")
    public void onDiscardButtonClick(Button.ClickEvent event) {
        close(StandardOutcome.DISCARD);
    }

    @Install(to = "personStepsTable", subject = "styleProvider")
    private String personStepsTableStyleProvider(PersonStep entity, String property) {
        if ("dueDate".equals(property) && isOverdue(entity)) {
            return "overdue-step";
        }
        return null;
    }
}