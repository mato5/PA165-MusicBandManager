package cz.fi.muni.pa165.dto;

/**
 * @author Miroslav Kadlec
 */
public class BandInviteCreateDTO {
    private Long bandId;
    private Long memberId;
    private Long managerId;

    public Long getBandId() {
        return this.bandId;
    }

    public void setBandId(Long band) {
        this.bandId = band;
    }

    public Long getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Long member) {
        this.memberId = member;
    }

    public Long getManagerId() {
        return this.managerId;
    }

    public void setManagerId(Long manager) {
        this.managerId = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof BandInviteCreateDTO)) return false;

        BandInviteCreateDTO bandInviteCreateDTO = (BandInviteCreateDTO) o;

        if (this.memberId != null ? !this.memberId.equals(bandInviteCreateDTO.getMemberId()) : bandInviteCreateDTO.getMemberId() != null) return false;
        if (this.managerId != null ? !this.managerId.equals(bandInviteCreateDTO.getManagerId()) : bandInviteCreateDTO.getManagerId() != null) return false;
        return this.bandId != null ? this.bandId.equals(bandInviteCreateDTO.getBandId()) : bandInviteCreateDTO.getBandId() == null;
    }

    @Override
    public int hashCode() {
        int result = this.memberId != null ? this.memberId.hashCode() : 0;
        result = 31 * result + (this.bandId != null ? this.bandId.hashCode() : 0);
        result = 31 * result + (this.managerId != null ? this.managerId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BandInviteDTO{" +
                "band=" + this.bandId +
                ", member=" + this.memberId +
                ", manager=" + this.managerId +
                '}';
    }
}
