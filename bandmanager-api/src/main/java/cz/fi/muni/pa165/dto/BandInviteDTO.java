package cz.fi.muni.pa165.dto;

/**
 * @author Alexander Kromka
 */
public class BandInviteDTO {
    private Long id;
    private BandDTO band;
    private MemberDTO member;
    private ManagerDTO manager;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BandDTO getBand() {
        return band;
    }

    public void setBand(BandDTO band) {
        this.band = band;
    }

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    public ManagerDTO getManager() {
        return manager;
    }

    public void setManager(ManagerDTO manager) {
        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof BandInviteDTO)) return false;

        BandInviteDTO bandInviteDTO = (BandInviteDTO) o;

        if (id != null ? !id.equals(bandInviteDTO.getId()) : bandInviteDTO.getId() != null) return false;
        if (member != null ? !member.equals(bandInviteDTO.getMember()) : bandInviteDTO.getMember() != null) return false;
        if (manager != null ? !manager.equals(bandInviteDTO.getManager()) : bandInviteDTO.getManager() != null) return false;
        return band != null ? band.equals(bandInviteDTO.getBand()) : bandInviteDTO.getBand() == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (member != null ? member.hashCode() : 0);
        result = 31 * result + (band != null ? band.hashCode() : 0);
        result = 31 * result + (manager != null ? manager.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BandInviteDTO{" +
                "id=" + id +
                ", band=" + band +
                ", member=" + member +
                ", manager=" + manager +
                '}';
    }
}
