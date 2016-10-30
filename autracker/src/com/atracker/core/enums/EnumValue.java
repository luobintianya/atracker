package com.atracker.core.enums;

import java.io.Serializable;

public abstract interface EnumValue extends Serializable
{

  public abstract String getType();

  public abstract String getCode();
}