package de.jcup.quickadopt;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class AdoptionContext{
	public Set<File> sourceFolderAdoptables = new TreeSet<>();
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("\n-----------------------------------\n");
		sb.append("sourceFolderAdoptables=\n");
		for (File file: sourceFolderAdoptables){
			try {
				sb.append(" ").append(file.getCanonicalPath()+"\n");
			} catch (IOException e) {
				sb.append(e.getMessage());
				break;
			}
		}
		return sb.toString();
	}
}