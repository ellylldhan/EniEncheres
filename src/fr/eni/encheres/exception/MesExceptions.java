/**
 * 
 */
package fr.eni.encheres.exception;

/**
 * Classe en charge de
 * @author thomas
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public abstract class MesExceptions extends Exception {
	
	private static final long serialVersionUID = 1L;
	private int code;
	
	public MesExceptions(int code) {
		super();
		this.code = code;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}

}
