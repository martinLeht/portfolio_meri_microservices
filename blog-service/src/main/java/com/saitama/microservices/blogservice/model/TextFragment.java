package com.saitama.microservices.blogservice.model;

public class TextFragment {
	
	private String text;
	
	private boolean bold;
	
	private boolean italic;
	
	private boolean underline;

	
	public TextFragment() {}

	
	public TextFragment(String text, boolean bold, boolean italic, boolean underline) {
		super();
		this.text = text;
		this.bold = bold;
		this.italic = italic;
		this.underline = underline;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public boolean isBold() {
		return bold;
	}


	public void setBold(boolean bold) {
		this.bold = bold;
	}


	public boolean isItalic() {
		return italic;
	}


	public void setItalic(boolean italic) {
		this.italic = italic;
	}


	public boolean isUnderline() {
		return underline;
	}


	public void setUnderline(boolean underline) {
		this.underline = underline;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (bold ? 1231 : 1237);
		result = prime * result + (italic ? 1231 : 1237);
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + (underline ? 1231 : 1237);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TextFragment other = (TextFragment) obj;
		if (bold != other.bold)
			return false;
		if (italic != other.italic)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (underline != other.underline)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "TextFragment [text=" + text + ", bold=" + bold + ", italic=" + italic + ", underline=" + underline
				+ "]";
	}
	
}
