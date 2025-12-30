package Logic;
import java.io.*;
import java.util.*;

public class Hospital implements Serializable {
    private final int id;
    private String name;

    private final LinkedList<Section> sections = new LinkedList<>();

    public Hospital(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Section getSection(int id) throws IDException {
        return sections.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IDException("Section not found"));
    }

    public Section getSection(String name) throws IDException {
        return sections.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IDException("Section not found"));
    }

    public LinkedList<Section> listSections(){
        return this.sections;
    }

    public void addSection(Section section) throws DuplicateInfoException {
        if (sections.stream().anyMatch(s -> s.getId() == section.getId())) {
            throw new DuplicateInfoException("Duplicate Section ID");
        }
        sections.add(section);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return this.name + "(" + this.id + ")";
    }
}
