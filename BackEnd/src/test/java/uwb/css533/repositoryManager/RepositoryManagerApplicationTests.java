package uwb.css533.repositoryManager;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.util.IOUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.mockito.internal.util.io.IOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;
import uwb.css533.repositoryManager.model.DependencyInfo;
import uwb.css533.repositoryManager.repo.IDependenciesRepo;
import uwb.css533.repositoryManager.uploadService.UploadServiceInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RepositoryManagerApplicationTests {

	@Autowired //Saving to Database so injecting IDependenciesRepo
	private IDependenciesRepo dependenciesRepo;

	@Autowired
	private MockMvc mockMvc; //To call the rest API provided by Spring framework
	private HttpClient httpClient;

	@Autowired
	private UploadServiceInterface uploadServiceInterface;

	//given/when/then format: BDD Behavioral Driven Development Style

	@Test
	public void givenDependencyInfo_whenPostRequestToAddValidDependency_thenCorrectResponse() throws Exception{

		String mockDependency = "{\"groupId\": \"com.github.com\", \"artifactId\" : \"spring\", \"version\" : \"3.1\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/dependencies/add").contentType(MediaType.APPLICATION_JSON)
				.content(mockDependency).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	@Test
	public void givenDependencyInfo_whenPostRequestToAddInvalidDependency_thenCorrectResponse() throws Exception{

		String mockDependency = "{\"groupId\": \"\", \"artifactId\" : \"spring\", \"version\" : \"2.3.1\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/dependencies/add")
						.contentType(MediaType.APPLICATION_JSON).content(mockDependency).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenDependencyInfoThatAlreadyExists_whenPostRequestToAddAlreadyExistingDependency_thenCorrectResponse() throws Exception{

		String mockDependency = "{\"groupId\": \"com.github.com\", \"artifactId\" : \"spring\", \"version\" : \"3.2\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/dependencies/add").contentType(MediaType.APPLICATION_JSON).content(mockDependency).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

	}

	@Test
	public void givenDependenciesList_whenGetAllDependencies_thenCorrectResponse() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/dependencies/all")).andExpect(status().isOk());
	}

	@Test
	public void givenKeyWord_whenGetRequestToDownloadDependencyThatExistsWithKeyWord_thenCorrectResponse() throws Exception{
		String keyName = "boot-3.2.0.jar";
		mockMvc.perform(MockMvcRequestBuilders.get("/dependencies/downloadFile/" + keyName)).andExpect(status().isOk());
	}

	@Test
	public void givenKeyWord_whenGetRequestToSearchDependenciesThatExistsWithKeyWord_thenCorrectResponse() throws Exception{
		String keyName = "spring";
		mockMvc.perform(MockMvcRequestBuilders.get("/dependencies/find/" + keyName)).andExpect(status().isOk());
	}

	@Test
	public void givenKeyWord_whenGetRequestToSearchDependenciesThatDoesNotExistsWithKeyWord_thenCorrectResponse() throws Exception{
		String keyName = "dummy";
		mockMvc.perform(MockMvcRequestBuilders.get("/dependencies/find/" + keyName)).andExpect(status().isOk());
	}

}
