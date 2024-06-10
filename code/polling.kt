@Scheduled(fixedDelay = 3000)
fun actualizeStatus() = loadTestDbService.findUnfinishedLoadTests()
    .forEach { loadTest ->
        try {
            loadTestService.getLoadTestStatus(loadTest.id!!)
            if (!consulProperties.consulEnabled) {
                systemConfigurationService.pollConfiguration(loadTest.id!!)
            }
        } catch (ex: SessionNotFoundException) {
            logger.error("Session not found, test will be skipped", ex)
        }
    }