package com.atracker.core.enums;

public abstract class EnumValueAbs implements EnumValue {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3363213633654551900L;

	private final String code;
	private final String codeLowerCase;
	
	public EnumValueAbs(String _code){

		this.code = _code.intern();
		this.codeLowerCase =  _code.toLowerCase().intern();
	}
	@Override
	public boolean equals(final Object obj)
	{
		try
		{
			final EnumValue enum2 = (EnumValue) obj;
			return this == enum2
			|| (this != null && enum2 != null && !this.getClass().isEnum() && !enum2.getClass().isEnum()
			&& this.getType().equalsIgnoreCase(enum2.getType()) && this.getCode().equalsIgnoreCase(enum2.getCode()));
		}
		catch (final ClassCastException e)
		{
			return false;
		}
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @return the codeLowerCase
	 */
	public String getCodeLowerCase() {
		return codeLowerCase;
	}
	
	
	@Override
	public String getType()
	{
		return getClass().getSimpleName();
	}
	
	@Override
	public int hashCode()
	{
		return this.getCodeLowerCase().hashCode();
	}

	@Override
	public String toString() {
		return String.valueOf(this.code);
	}
	
	
}
