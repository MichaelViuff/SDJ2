package Util;

import Model.Model;

public class ModelFactory
{
    private Model model;

    public Model getModel()
    {
        if(model == null)
        {
            model = new Model();
        }
        return model;
    }
}
