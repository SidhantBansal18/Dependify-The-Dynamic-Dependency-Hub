package uwb.css533.repositoryManager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

//Class that will create the database with columns

@Entity
@Builder //Used during integration tests
@AllArgsConstructor //Used during integration tests
@Table(name = "dependency_info")
public class DependencyInfo implements Serializable {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private int id;

    @NotBlank(message = "Group ID is mandatory")
    private String groupId;

    @NotBlank(message = "Artifact ID is mandatory")
    private String artifactId;

    @NotBlank(message = "Version is mandatory")
    private String version;

    private String search;

    private java.util.Date lastModifiedDate;

    public DependencyInfo() {
    }

    public int getId() {
        return id;
    }

    //Using key of a dependency as a combination of artifact id and version number
    public void setId(int id) {
        this.id = id;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = artifactId+ "-" + version + ".jar";
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = new Date();
    }

    @Override
    public String toString() {
        return "DependencyInfo{" +
                "id=" + id +
                "search=" + search +
                ", groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version=" + version + '\'' +
                ", lastModifiedDate=" + lastModifiedDate + '\'' +
                '}';
    }
}
