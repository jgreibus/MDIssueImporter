package lt.jgreibus.magicdraw.redmine.element.manager;

import com.nomagic.magicdraw.ui.dialogs.MDDialogParentProvider;
import com.nomagic.magicdraw.ui.dialogs.SelectElementInfo;
import com.nomagic.magicdraw.ui.dialogs.SelectElementTypes;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlg;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlgFactory;
import com.nomagic.magicdraw.ui.dialogs.selection.TypeFilter;
import com.nomagic.magicdraw.ui.dialogs.selection.TypeFilterImpl;
import com.nomagic.magicdraw.uml.BaseElement;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;

public class ElementSelectionManager {
    public static ElementSelectionDlg createElementSelectionDialog(List types) {

        SelectElementTypes selectElementTypes = new SelectElementTypes(types, types, null, types);
        TypeFilter selectableFilter = new TypeFilterImpl(selectElementTypes.select) {
            @Override
            public boolean accept(@Nonnull BaseElement baseElement, boolean checkType) {
                return super.accept(baseElement, checkType);
            }
        };

        TypeFilter visibleFilter = new TypeFilterImpl(selectElementTypes.display) {
            @Override
            public boolean accept(@Nonnull BaseElement baseElement, boolean checkType) {
                return super.accept(baseElement, checkType);
            }
        };

        Frame dialogParent = MDDialogParentProvider.getProvider().getDialogParent();
        ElementSelectionDlg selectionDlg = ElementSelectionDlgFactory.create(dialogParent, "Select element", null);

        SelectElementInfo selectElementInfo = new SelectElementInfo(true, false, null, true);
        ElementSelectionDlgFactory.initSingle(selectionDlg, selectElementInfo, visibleFilter, selectableFilter, null, null);

        return selectionDlg;
    }
}
