package pl.empik.couponservice.external.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.empik.couponservice.external.model.CountryResponse;

@FeignClient(name = "countryClient", url = "${coupon-service.external.country.url}")
public interface CountryClient {
    @GetMapping("/{ip}")
    CountryResponse getCountry(@PathVariable("ip") String ip);
}