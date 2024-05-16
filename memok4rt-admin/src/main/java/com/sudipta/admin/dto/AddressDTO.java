package  com.sudipta.admin.dto;

import lombok.Data;

@Data
public class AddressDTO {
	private Long id;
	private String firstName;
	private String lastName;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private UserDTO user;
    private String mobile;
}

