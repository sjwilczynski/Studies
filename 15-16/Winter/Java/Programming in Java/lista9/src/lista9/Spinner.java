package lista9;

import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;


public class Spinner extends SpinnerListModel 
{
    Object firstValue, lastValue;
    SpinnerModel linkedModel = null;

    public Spinner(Object[] values) 
    {
        super(values);
        firstValue = values[0];
        lastValue = values[values.length - 1];
    }

    public void setLinkedModel(SpinnerModel linkedModel) 
    {
        this.linkedModel = linkedModel;
    }

    @Override
    public Object getNextValue() 
    {
        Object value = super.getNextValue();
        if (value == null) {
            value = firstValue;
            Lista9.currentYear++;
            if (linkedModel != null) 
            {
                linkedModel.setValue(linkedModel.getNextValue());
            }
        }
        return value;
    }

    @Override
    public Object getPreviousValue() 
    {
        Object value = super.getPreviousValue();
        if (value == null) {
            value = lastValue;
            Lista9.currentYear--;
            if (linkedModel != null) 
            {
                linkedModel.setValue(linkedModel.getPreviousValue());
            }
        }
        return value;
    }
}