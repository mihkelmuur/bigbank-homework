package ee.mihkelmuur.bigbank.game;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RunnerTests {

    @Mock
    private ApiClient apiClient;

    @Mock
    private AdConverter adConverter;

    @Mock
    private AdPicker adPicker;

    @InjectMocks
    private Runner runner;

    // TODO: Write tests

}
