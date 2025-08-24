package be.pxl.services.domain.dto;

import be.pxl.services.domain.WishlistItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistRequest {
    private Long id;
    private Long userId;
    private List<WishlistItem> items;
}
