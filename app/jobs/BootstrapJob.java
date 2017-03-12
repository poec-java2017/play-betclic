package jobs;

import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class BootstrapJob extends Job {

    @Override
    public void doJob() throws Exception {
        Fixtures.deleteDatabase();
        Fixtures.loadModels("dataUser.yml");
        Fixtures.loadModels("dataEvent.yml");
        Fixtures.loadModels("dataMoney.yml");
        Fixtures.loadModels("ApiClients.yml");
    }
}