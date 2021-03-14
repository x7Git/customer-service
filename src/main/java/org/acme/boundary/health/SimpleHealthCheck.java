package org.acme.boundary.health;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class SimpleHealthCheck implements org.eclipse.microprofile.health.HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.up("System is running");
    }
}
