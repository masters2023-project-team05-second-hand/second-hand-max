package team05a.secondhand.image.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import team05a.secondhand.errors.exception.ImageNotFoundException;
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

	public List<String> uploadProductImages(List<MultipartFile> images) {
		validateImageCount(images);

		List<String> imageUrls = new ArrayList<>();
		for (MultipartFile image : images) {
			imageUrls.add(uploadImage(image));
		}

		return imageUrls;
	}

	public String uploadImage(MultipartFile image) {
		String imageName = image.getOriginalFilename() + UUID.randomUUID();
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(image.getContentType());

		try {
			amazonS3Client.putObject(
				new PutObjectRequest(bucketName, imageName, image.getInputStream(), objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead));
			return amazonS3Client.getUrl(bucketName, imageName).toString();
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
		if (deletedImageIds != null) {
			imageRepository.deleteAllByIdInBatch(deletedImageIds);
		}
	}

	public int countImagesBy(Long productId) {
		return imageRepository.countByProductId(productId);
	}

	public List<String> uploadNew(int imageCount, List<MultipartFile> newImages) {
		int newImageCount = newImages == null ? 0 : newImages.size();
		validateAllImageCount(newImageCount + imageCount);

		if (newImageCount == 0) {
			return new ArrayList<>();
		}
		return uploadProductImages(newImages);
	}

	private void validateAllImageCount(int imageCount) {
		if (imageCount == 0 || imageCount > 10) {
			throw new ImageCountOutOfRangeException();
		}
	}

	public String findThumbnail(Long productId) {
		return imageRepository.findFirstByProductId(productId)
			.orElseThrow(ImageNotFoundException::new)
			.getImageUrl();
	}

	public List<ProductImage> findAllByProduct(Product product) {
		return imageRepository.findAllByProduct(product);
	}
}
