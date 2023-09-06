package team05a.secondhand.image.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.errors.exception.ImageCountOutOfRangeException;
import team05a.secondhand.errors.exception.ImageUploadFailedException;
import team05a.secondhand.image.data.entity.ProductImage;
import team05a.secondhand.image.repository.ImageRepository;
import team05a.secondhand.product.data.entity.Product;

@Transactional
@RequiredArgsConstructor
@Service
public class ImageService {

	private final AmazonS3Client amazonS3Client;
	private final ImageRepository imageRepository;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	public List<String> upload(List<MultipartFile> images) {
		validateImageCount(images);

		List<String> imageUrls = new ArrayList<>();
		for (MultipartFile image : images) {
			uploadImage(imageUrls, image);
		}

		return imageUrls;
	}

	private void uploadImage(List<String> imageUrls, MultipartFile image) {
		String imageName = image.getOriginalFilename() + UUID.randomUUID();
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(image.getContentType());
		try {
			amazonS3Client.putObject(
				new PutObjectRequest(bucketName, imageName, image.getInputStream(), objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead));
			imageUrls.add(amazonS3Client.getUrl(bucketName, imageName).toString());
		} catch (IOException e) {
			throw new ImageUploadFailedException();
		}
	}

	private void validateImageCount(List<MultipartFile> images) {
		if (images == null || images.size() > 10) {
			throw new ImageCountOutOfRangeException();
		}
	}

	public List<ProductImage> create(Product product, List<String> imageUrls) {
		List<ProductImage> productImages = imageUrls.stream()
			.map(imageUrl -> ProductImage.builder()
				.product(product)
				.imageUrl(imageUrl)
				.build())
			.collect(Collectors.toList());
		return imageRepository.saveAll(productImages);
	}

	public void deleteAllBy(List<Long> deletedImageIds) {
		if (!deletedImageIds.isEmpty()) {
			imageRepository.deleteAllByIdInBatch(deletedImageIds);
		}
	}

	public Long count(Long productId) {
		return imageRepository.countByProductId(productId);
	}

	public List<String> uploadNew(Long imageCount, List<MultipartFile> newImages) {
		if ((newImages == null && imageCount == 0) || (imageCount + Objects.requireNonNull(newImages).size()) > 10) {
			throw new ImageCountOutOfRangeException();
		}
		return upload(newImages);
	}

	public String findThumbnail(Product product) {
		return imageRepository.findFirstByProduct(product).getImageUrl();
	}
}
