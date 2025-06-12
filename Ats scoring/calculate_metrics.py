import pandas as pd
import torch
from transformers import BertTokenizer, BertModel
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score, confusion_matrix, classification_report

# Load BERT model and tokenizer
tokenizer = BertTokenizer.from_pretrained('bert-base-uncased')
model = BertModel.from_pretrained('bert-base-uncased')

# Load ground truth data
df = pd.read_csv('evaluation_data.csv')

# Display column names for debugging
print("Columns in the CSV:", df.columns)

# Function to get BERT embeddings
def get_bert_embedding(text):
    inputs = tokenizer(text, return_tensors='pt', padding=True, truncation=True, max_length=512)
    with torch.no_grad():
        outputs = model(**inputs)
    return outputs.last_hidden_state.mean(dim=1).squeeze().numpy()

# Calculate ATS scores
def calculate_ats_score(resume, job_description):
    resume_embedding = get_bert_embedding(resume)
    job_embedding = get_bert_embedding(job_description)
    cosine_sim = cosine_similarity([resume_embedding], [job_embedding])
    return round(float(cosine_sim[0][0]) * 100, 2)

# Add ATS scores and predicted labels
df['Predicted Score'] = df.apply(lambda row: calculate_ats_score(str(row['Resume']), str(row['Job Description'])), axis=1)

# Set threshold for matching
threshold = 70
df['Predicted Label'] = df['Predicted Score'].apply(lambda x: 1 if x >= threshold else 0)

# Save predictions to CSV
df.to_csv('predicted_labels.csv', index=False)

# Calculate overall metrics
y_true = df['True Label'].tolist()
y_pred = df['Predicted Label'].tolist()

accuracy = accuracy_score(y_true, y_pred)
precision = precision_score(y_true, y_pred)
recall = recall_score(y_true, y_pred)
f1 = f1_score(y_true, y_pred)

# Display overall metrics
print("Confusion Matrix:\n", confusion_matrix(y_true, y_pred))
print("Classification Report:\n", classification_report(y_true, y_pred))
print(f"Overall Accuracy: {accuracy:.2f}")
print(f"Overall Precision: {precision:.2f}")
print(f"Overall Recall: {recall:.2f}")
print(f"Overall F1 Score: {f1:.2f}")

# Calculate and print metrics for each template (in CSV order)
template_metrics = []
seen_templates = []

for _, row in df.iterrows():
    template = row['Resume']
    if template not in seen_templates:
        template_df = df[df['Resume'] == template]
        y_true = template_df['True Label'].tolist()
        y_pred = template_df['Predicted Label'].tolist()
        accuracy = accuracy_score(y_true, y_pred)
        precision = precision_score(y_true, y_pred, zero_division=0)
        recall = recall_score(y_true, y_pred, zero_division=0)
        f1 = f1_score(y_true, y_pred, zero_division=0)
        template_metrics.append([template, accuracy, precision, recall, f1])
        seen_templates.append(template)

# Convert to DataFrame for tabular display
template_metrics_df = pd.DataFrame(template_metrics, columns=['Template', 'Accuracy', 'Precision', 'Recall', 'F1 Score'])

# Display template-wise metrics
print("\nTemplate-wise Metrics (Ordered by CSV):\n")
print(template_metrics_df.to_string(index=False))

# Save template metrics to CSV
template_metrics_df.to_csv('template_metrics.csv', index=False)
print("Template-wise metrics saved to template_metrics.csv")



# import pandas as pd
# import torch
# from transformers import BertTokenizer, BertModel
# from sklearn.metrics.pairwise import cosine_similarity
# from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score, confusion_matrix, classification_report

# # Load BERT model and tokenizer
# tokenizer = BertTokenizer.from_pretrained('bert-base-uncased')
# model = BertModel.from_pretrained('bert-base-uncased')

# # Load ground truth data
# df = pd.read_csv('evaluation_data.csv')

# # Display column names for debugging
# print("Columns in the CSV:", df.columns)

# # Function to get BERT embeddings
# def get_bert_embedding(text):
#     inputs = tokenizer(text, return_tensors='pt', padding=True, truncation=True, max_length=512)
#     with torch.no_grad():
#         outputs = model(**inputs)
#     return outputs.last_hidden_state.mean(dim=1).squeeze().numpy()

# # Calculate ATS scores
# def calculate_ats_score(resume, job_description):
#     resume_embedding = get_bert_embedding(resume)
#     job_embedding = get_bert_embedding(job_description)
#     cosine_sim = cosine_similarity([resume_embedding], [job_embedding])
#     return round(float(cosine_sim[0][0]) * 100, 2)

# # Add ATS scores and predicted labels
# df['Predicted Score'] = df.apply(lambda row: calculate_ats_score(str(row['Resume']), str(row['Job Description'])), axis=1)

# # Set threshold for matching
# threshold = 70
# df['Predicted Label'] = df['Predicted Score'].apply(lambda x: 1 if x >= threshold else 0)

# # Save predictions to CSV
# df.to_csv('predicted_labels.csv', index=False)

# # Calculate metrics
# y_true = df['True Label'].tolist()
# y_pred = df['Predicted Label'].tolist()

# accuracy = accuracy_score(y_true, y_pred)
# precision = precision_score(y_true, y_pred)
# recall = recall_score(y_true, y_pred)
# f1 = f1_score(y_true, y_pred)

# # Display metrics
# print("Confusion Matrix:\n", confusion_matrix(y_true, y_pred))
# print("Classification Report:\n", classification_report(y_true, y_pred))
# print(f"Accuracy: {accuracy:.2f}")
# print(f"Precision: {precision:.2f}")
# print(f"Recall: {recall:.2f}")
# print(f"F1 Score: {f1:.2f}")

# # Calculate and print metrics for each resume template
# template_metrics = []

# grouped = df.groupby(df['Resume'].str.split(':').str[0])
# for template_name, group in grouped:
#     y_true = group['True Label'].tolist()
#     y_pred = group['Predicted Label'].tolist()
#     accuracy = accuracy_score(y_true, y_pred)
#     precision = precision_score(y_true, y_pred, zero_division=0)
#     recall = recall_score(y_true, y_pred, zero_division=0)
#     f1 = f1_score(y_true, y_pred, zero_division=0)
#     template_metrics.append([template_name, accuracy, precision, recall, f1])

# # Convert to DataFrame for tabular display
# template_metrics_df = pd.DataFrame(template_metrics, columns=['Template', 'Accuracy', 'Precision', 'Recall', 'F1 Score'])
# print("\nTemplate-wise Metrics:\n")
# print(template_metrics_df.to_string(index=False))

# # Save template metrics to CSV
# template_metrics_df.to_csv('template_metrics.csv', index=False)
# print("Template-wise metrics saved to template_metrics.csv")



# import pandas as pd
# import torch
# from transformers import BertTokenizer, BertModel
# from sklearn.metrics.pairwise import cosine_similarity
# from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score, confusion_matrix, classification_report

# # Load BERT model and tokenizer
# tokenizer = BertTokenizer.from_pretrained('bert-base-uncased')
# model = BertModel.from_pretrained('bert-base-uncased')

# # Load ground truth data
# df = pd.read_csv('evaluation_data.csv')

# # Function to get BERT embeddings
# def get_bert_embedding(text):
#     inputs = tokenizer(text, return_tensors='pt', padding=True, truncation=True, max_length=512)
#     with torch.no_grad():
#         outputs = model(**inputs)
#     return outputs.last_hidden_state.mean(dim=1).squeeze().numpy()

# # Calculate ATS scores
# def calculate_ats_score(resume, job_description):
#     resume_embedding = get_bert_embedding(resume)
#     job_embedding = get_bert_embedding(job_description)
#     cosine_sim = cosine_similarity([resume_embedding], [job_embedding])
#     return round(float(cosine_sim[0][0]) * 100, 2)

# # Add ATS scores and predicted labels
# df['Predicted Score'] = df.apply(lambda row: calculate_ats_score(row['Resume'], row['Job Description']), axis=1)

# # Set threshold for matching
# threshold = 70
# df['Predicted Label'] = df['Predicted Score'].apply(lambda x: 1 if x >= threshold else 0)

# # Save predictions to CSV
# df.to_csv('predicted_labels.csv', index=False)

# # Calculate metrics
# y_true = df['True Label'].tolist()
# y_pred = df['Predicted Label'].tolist()

# accuracy = accuracy_score(y_true, y_pred)
# precision = precision_score(y_true, y_pred)
# recall = recall_score(y_true, y_pred)
# f1 = f1_score(y_true, y_pred)

# # Display metrics
# print("Confusion Matrix:\n", confusion_matrix(y_true, y_pred))
# print("Classification Report:\n", classification_report(y_true, y_pred))
# print(f"Accuracy: {accuracy:.2f}")
# print(f"Precision: {precision:.2f}")
# print(f"Recall: {recall:.2f}")
# print(f"F1 Score: {f1:.2f}")

