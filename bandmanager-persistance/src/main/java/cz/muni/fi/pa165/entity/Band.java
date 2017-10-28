package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.enums.Genre;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Iurii xkuznetc Kuznetcov
 */
@Entity
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    private String logoURI;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    //@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Manager manager;

    @ManyToMany
    private Set<Member> members = new HashSet<Member>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoURI() {
        return logoURI;
    }

    public void setLogoURI(String logoURI) {
        this.logoURI = logoURI;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void removeManager(Manager manager) {
        this.manager = null;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Manager getManager() {
        return this.manager;
    }

    public void removeMember(Member member) {
        this.members.remove(member);
    }

    public void addMember(Member member) {
        members.add(member);
        member.setBand(this);
    }

    public Set<Member> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    public Band() {
    }

    public Band(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Band)) return false;

        Band band = (Band) o;

        return name.equals(band.name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Band{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logoURI='" + logoURI + '\'' +
                ", genre=" + genre +
                ", manager=" + manager +
                ", members=" + members +
                '}';
    }
}
