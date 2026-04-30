package pl.empik.couponservice.external;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.empik.couponservice.exception.CouponServiceException;
import pl.empik.couponservice.external.http.CountryClient;

@Service
@Slf4j
@AllArgsConstructor
public class IpAddressService {

    private final CountryClient countryClient;

    public String getCountryCodeByIp(String ipAddress) {
        if (StringUtils.isBlank(ipAddress)) {
            throw new CouponServiceException("ipAddress is blank");
        }
        log.info("Fetching country code by IP from external service: {}", ipAddress);
        var country = countryClient.getCountry(ipAddress);
        log.info("Fetched country code by IP from external service: {}", country);
        return country.country();
    }
}
