import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score

# Load ground truth data
df = pd.read_csv('evaluation_data.csv')

# Function to calculate cosine similarity using TF-IDF
def calculate_tfidf_score(resume, job_description):
    vectorizer = TfidfVectorizer()
    tfidf_matrix = vectorizer.fit_transform([resume, job_description])
    cosine_sim = cosine_similarity(tfidf_matrix[0:1], tfidf_matrix[1:2])
    return round(float(cosine_sim[0][0]) * 100, 2)

# Add ATS scores to dataframe
df['Predicted Score'] = df.apply(lambda row: calculate_tfidf_score(row['Resume'], row['Job Description']), axis=1)

# Set a threshold for matching (70 or any value you prefer)
threshold = 70
df['Predicted Label'] = df['Predicted Score'].apply(lambda x: 1 if x >= threshold else 0)

# Calculate overall metrics
overall_accuracy = accuracy_score(df['True Label'], df['Predicted Label'])
overall_precision = precision_score(df['True Label'], df['Predicted Label'], zero_division=0)
overall_recall = recall_score(df['True Label'], df['Predicted Label'], zero_division=0)
overall_f1 = f1_score(df['True Label'], df['Predicted Label'], zero_division=0)

# Save predictions to CSV
df.to_csv('predicted_labels_tfidf.csv', index=False)

# Display overall metrics
print("Overall Metrics for TF-IDF ATS Scoring Algorithm:")
print(f"Accuracy: {overall_accuracy:.2f}")
print(f"Precision: {overall_precision:.2f}")
print(f"Recall: {overall_recall:.2f}")
print(f"F1 Score: {overall_f1:.2f}")

# Calculate template-wise metrics
template_metrics = []
for template in df['Resume'].unique():
    template_df = df[df['Resume'] == template]
    accuracy = accuracy_score(template_df['True Label'], template_df['Predicted Label'])
    precision = precision_score(template_df['True Label'], template_df['Predicted Label'], zero_division=0)
    recall = recall_score(template_df['True Label'], template_df['Predicted Label'], zero_division=0)
    f1 = f1_score(template_df['True Label'], template_df['Predicted Label'], zero_division=0)
    template_metrics.append([template, accuracy, precision, recall, f1])

# Convert to dataframe for better display
template_metrics_df = pd.DataFrame(template_metrics, columns=['Template', 'Accuracy', 'Precision', 'Recall', 'F1 Score'])

# Display template-wise metrics in a table
print("\nTemplate-wise Metrics for TF-IDF ATS Scoring Algorithm:\n")
print(template_metrics_df.to_string(index=False, formatters={
    'Accuracy': '{:.2f}'.format,
    'Precision': '{:.2f}'.format,
    'Recall': '{:.2f}'.format,
    'F1 Score': '{:.2f}'.format
}))



# import pandas as pd
# from sklearn.feature_extraction.text import TfidfVectorizer
# from sklearn.metrics.pairwise import cosine_similarity
# from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score

# # Load ground truth data
# df = pd.read_csv('evaluation_data.csv')

# # Function to calculate cosine similarity using TF-IDF
# def calculate_tfidf_score(resume, job_description):
#     vectorizer = TfidfVectorizer()
#     tfidf_matrix = vectorizer.fit_transform([resume, job_description])
#     cosine_sim = cosine_similarity(tfidf_matrix[0:1], tfidf_matrix[1:2])
#     return round(float(cosine_sim[0][0]) * 100, 2)

# # Add ATS scores to dataframe
# df['Predicted Score'] = df.apply(lambda row: calculate_tfidf_score(row['Resume'], row['Job Description']), axis=1)

# # Set a threshold for matching (70 or any value you prefer)
# threshold = 70
# df['Predicted Label'] = df['Predicted Score'].apply(lambda x: 1 if x >= threshold else 0)

# # Calculate metrics
# accuracy = accuracy_score(df['True Label'], df['Predicted Label'])
# precision = precision_score(df['True Label'], df['Predicted Label'])
# recall = recall_score(df['True Label'], df['Predicted Label'])
# f1 = f1_score(df['True Label'], df['Predicted Label'])

# # Save predictions to CSV
# df.to_csv('predicted_labels_tfidf.csv', index=False)

# # Display results
# print("Metrics for TF-IDF ATS Scoring Algorithm:")
# print(f"Accuracy: {accuracy:.2f}")
# print(f"Precision: {precision:.2f}")
# print(f"Recall: {recall:.2f}")
# print(f"F1 Score: {f1:.2f}")

# print(df[['Resume', 'Job Description', 'True Label', 'Predicted Score', 'Predicted Label']])