/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.management.rest.resource;

import io.gravitee.common.http.MediaType;
import io.gravitee.management.model.analytics.Analytics;
import io.gravitee.management.rest.resource.param.AnalyticsParam;
import io.gravitee.management.service.AnalyticsService;
import io.swagger.annotations.Api;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * @author Titouan COMPIEGNE (titouan.compiegne at graviteesource.com)
 * @author GraviteeSource Team
 */
@Api(tags = {"Gateway"})
public class PlatformAnalyticsResource {

    @Inject
    private AnalyticsService analyticsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response platformAnalytics(@BeanParam AnalyticsParam analyticsParam) {

        analyticsParam.validate();

        Analytics analytics = null;

        switch(analyticsParam.getTypeParam().getValue()) {
            case HITS_BY:
                analytics = analyticsService.hitsBy(
                        analyticsParam.getQuery(),
                        analyticsParam.getKey(),
                        analyticsParam.getField(),
                        analyticsParam.getAggType(),
                        analyticsParam.getFrom(),
                        analyticsParam.getTo(),
                        analyticsParam.getInterval());
                break;
            case GLOBAL_HITS:
                analytics = analyticsService.globalHits(
                        analyticsParam.getQuery(),
                        analyticsParam.getKey(),
                        analyticsParam.getFrom(),
                        analyticsParam.getTo());
                break;
            case TOP_HITS:
                analytics = analyticsService.topHits(
                        analyticsParam.getQuery(),
                        analyticsParam.getKey(),
                        analyticsParam.getField(),
                        analyticsParam.getOrder(),
                        analyticsParam.getFrom(),
                        analyticsParam.getTo(),
                        analyticsParam.getSize());
                break;
        }

        return Response.ok(analytics).build();
    }
}