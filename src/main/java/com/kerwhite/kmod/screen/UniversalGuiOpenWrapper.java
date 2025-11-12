package com.kerwhite.kmod.screen;

import com.kerwhite.kmod.KmodRuntimeException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
@SuppressWarnings("ALL")
public class UniversalGuiOpenWrapper <T extends Screen>
{
    private T screen;
    private Class<T> tClass;
    private ArrayList<Object> args = new ArrayList<>();
    private ArrayList<Class> argsClass = new ArrayList<>();
    private Constructor<T> constructor = null;
    public static <T extends Screen> UniversalGuiOpenWrapper<T> newInstance(Class<T> cls,Object... args)
    {
        return new UniversalGuiOpenWrapper<>(cls, args);
    }
    public UniversalGuiOpenWrapper(Class<T> cls,Object... args)
    {
        this.tClass = cls;
        this.args.addAll(Arrays.asList(args));
    }
    public void setScreen()
    {
        Minecraft.getInstance().setScreen(this.getScreen());
    }
    public UniversalGuiOpenWrapper<T> addArgsClass(Class... cls)
    {
        this.argsClass.addAll(Arrays.asList(cls));
        return this;
    }
    public UniversalGuiOpenWrapper<T> addArgs(Object... objs)
    {
        for(Object obj : objs)
        {
            this.args.add(obj);
        }
        return this;
    }
    public T getScreen()
    {
        return this.screen;
    }
    public UniversalGuiOpenWrapper<T> autoArgClass()
    {
        for(Constructor<T> constructor : (Constructor<T>[]) this.tClass.getDeclaredConstructors())
        {
            int i = 0;
            boolean flag = true;
            if(this.args.size()!=constructor.getParameterTypes().length){continue;}
            for(Class cls : constructor.getParameterTypes())
            {
                if(!cls.isAssignableFrom(this.args.get(i).getClass()))
                {
                    flag = false;
                    break;
                }
                i++;
            }
            if(flag)
            {
                this.constructor = constructor;
            }
        }
        return this;
    }
    private void buildScreen(Constructor<T> constructor)
    {
        Object[] paramsArray = this.args.toArray();
        try
        {
            this.screen = constructor.newInstance(paramsArray);
        }
        catch (InstantiationException | InvocationTargetException | IllegalAccessException e)
        {
            throw new KmodRuntimeException(e);
        }
    }
    public UniversalGuiOpenWrapper<T> build() throws KmodRuntimeException
    {
        boolean hasConstructor = false;
        if(this.constructor!=null)
        {
            this.buildScreen(this.constructor);
        }
        else
        {
            for(Constructor<T> constructor : (Constructor<T>[]) this.tClass.getDeclaredConstructors())
            {
                int i = 0;
                boolean flag = true;
                if(this.argsClass.size()!=constructor.getParameterTypes().length){break;}
                for(Class cls : constructor.getParameterTypes())
                {
                    if(cls != this.argsClass.get(i))
                    {
                        flag = false;
                        break;
                    }
                    i++;
                }
                if(flag)
                {
                    hasConstructor = true;
                    this.buildScreen(constructor);
                }
            }
            if(!hasConstructor)
            {
                throw new KmodRuntimeException("this.argsClass dont't match to any parameter of class!");
            }
        }
        return this;
    }
}
