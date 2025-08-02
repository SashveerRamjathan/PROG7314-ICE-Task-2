package com.st10361554.prog7314_ice_task_2_chatbot.models

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a citation reference in chatbot responses.
 *
 * This class models the structure of a citation, including its position in the text,
 * the cited text itself, associated document IDs, and the type of citation.
 *
 * @property start The start index (inclusive) of the citation within the response text.
 * @property end The end index (exclusive) of the citation within the response text.
 * @property text The actual text that is being cited.
 * @property documentIds A list of document IDs associated with this citation.
 * @property type The type of citation (e.g., "web", "book", etc.).
 */
data class Citation(
    @SerializedName("start")
    val start: Int, // Start index of the citation in the text

    @SerializedName("end")
    val end: Int, // End index of the citation in the text

    @SerializedName("text")
    val text: String, // The quoted or cited text

    @SerializedName("documentIds")
    val documentIds: List<String>, // IDs of the documents referenced

    @SerializedName("type")
    val type: String // The type/category of the citation
)